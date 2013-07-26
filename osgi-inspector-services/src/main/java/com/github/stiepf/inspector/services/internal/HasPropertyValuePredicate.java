package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.ServiceReference;

class HasPropertyValuePredicate extends ServicePredicate {

  private final String key;
  
  private final Object value;
    
  HasPropertyValuePredicate(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  @Override
  boolean matches(ServiceReference reference) {
    Object property = reference.getProperty(key);
    if (property != null) {
      return property.equals(value);
    } else {
      return false;
    }
  }

}
