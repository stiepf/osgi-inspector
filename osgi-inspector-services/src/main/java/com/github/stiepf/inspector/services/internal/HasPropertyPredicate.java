package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.ServiceReference;

class HasPropertyPredicate extends ServicePredicate {

  private final String key;
  
  HasPropertyPredicate(String key) {
    this.key = key;
  }

  @Override
  boolean matches(ServiceReference reference) {
    return reference.getProperty(key) != null;
  }

}
