/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.allstate.trobo.vehiclerouting;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;

import com.allstate.trobo.domain.TripSheet;

public class VehicleRoutingSolverManager1 implements Serializable {

    private static final String SOLVER_CONFIG = "org/optaplanner/webexamples/vehiclerouting/vehicleRoutingSolverConfig.xml";

    private SolverFactory solverFactory;
    // TODO After upgrading to JEE 7, replace ExecutorService by ManagedExecutorService:
    // @Resource(name = "DefaultManagedExecutorService")
    // private ManagedExecutorService executor;
    private ExecutorService executor;

    private Map<String, VehicleRoutingSolution> sessionSolutionMap;
    private Map<String, Solver> sessionSolverMap;
    
    private VehicleRoutingSolverManager1() {
    	
    }
    
    private static VehicleRoutingSolverManager1 INSTANCE;
    
    public static synchronized VehicleRoutingSolverManager1 getInstance() {
    	if(INSTANCE == null) {
    		INSTANCE = new VehicleRoutingSolverManager1();
    		INSTANCE.init();
    	}
    	return INSTANCE;
    }

    @PostConstruct
    public synchronized void init() {
        solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        // Always terminate a solver after 2 minutes
        TerminationConfig terminationConfig = new TerminationConfig();
        terminationConfig.setMinutesSpentLimit(2L);
        solverFactory.getSolverConfig().setTerminationConfig(terminationConfig);
        executor = Executors.newFixedThreadPool(2); // Only 2 because the other examples have their own Executor
        // TODO these probably don't need to be thread-safe because all access is synchronized
        sessionSolutionMap = new ConcurrentHashMap<String, VehicleRoutingSolution>();
        sessionSolverMap = new ConcurrentHashMap<String, Solver>();
    }

    @PreDestroy
    public synchronized void destroy() {
        for (Solver solver : sessionSolverMap.values()) {
            solver.terminateEarly();
        }
        executor.shutdown();
    }
    
	public synchronized VehicleRoutingSolution createSolution(String sessionId,
			TripSheet tripSheet) {
		VehicleRoutingSolution solution = (VehicleRoutingSolution) new VehicleRoutingImporter(true).readSolution(tripSheet);
		sessionSolutionMap.put(sessionId, solution);
		return solution;
	}

    public synchronized VehicleRoutingSolution retrieveOrCreateSolution(String sessionId, TripSheet tripSheet) {
        VehicleRoutingSolution solution = sessionSolutionMap.get(sessionId);
        if (solution == null) {
            solution = (VehicleRoutingSolution) new VehicleRoutingImporter(true)
                    .readSolution(tripSheet);
            sessionSolutionMap.put(sessionId, solution);
        }
        return solution;
    }

    public synchronized boolean solve(final String sessionId) {
        final Solver solver = solverFactory.buildSolver();
        solver.addEventListener(new SolverEventListener() {
            public void bestSolutionChanged(BestSolutionChangedEvent event) {
                VehicleRoutingSolution bestSolution = (VehicleRoutingSolution) event.getNewBestSolution();
                synchronized (VehicleRoutingSolverManager1.this) {
                    sessionSolutionMap.put(sessionId, bestSolution);
                }
            }
        });
        if (sessionSolverMap.containsKey(sessionId)) {
            return false;
        }
        sessionSolverMap.put(sessionId, solver);
        final VehicleRoutingSolution solution = retrieveOrCreateSolution(sessionId, null);
        executor.submit(new Runnable() {
            public void run() {
                solver.solve(solution);
                VehicleRoutingSolution bestSolution = (VehicleRoutingSolution) solver.getBestSolution();
                synchronized (VehicleRoutingSolverManager1.this) {
                    sessionSolutionMap.put(sessionId, bestSolution);
                    sessionSolverMap.remove(sessionId);
                }
            }
        });
        return true;
    }

    public synchronized boolean terminateEarly(String sessionId) {
        Solver solver = sessionSolverMap.remove(sessionId);
        if (solver != null) {
            solver.terminateEarly();
            return true;
        } else {
            return false;
        }
    }

}
