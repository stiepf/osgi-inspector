package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

class ProvidedByPredicate extends ServicePredicate {

  private final Bundle bundle;
  
  ProvidedByPredicate(Bundle bundle) {
    this.bundle = bundle;
  }

  @Override
  boolean matches(ServiceReference reference) {
    return reference.getBundle().equals(bundle);
  }

}
