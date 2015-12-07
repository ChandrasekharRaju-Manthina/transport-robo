package com.allstate.trobo.dao;

import com.allstate.trobo.domain.Spitter;

public interface SpitterRepository {

  Spitter save(Spitter spitter);
  
  Spitter findByUsername(String username);

}
