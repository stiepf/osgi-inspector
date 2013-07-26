package com.github.stiepf.inspector.components.internal;

import org.osgi.framework.ServiceReference;

class SymbolicNamePredicate extends ServiceReferencePredicate {

  private final String symbolicName;
  
  SymbolicNamePredicate(String symbolicName) {
    this.symbolicName = symbolicName;
  }

  @Override
  boolean matches(ServiceReference reference) {
    return reference.getBundle().getSymbolicName().equals(symbolicName);
  }

}
