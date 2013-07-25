package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

class ProvidesServicePredicate extends ServicePredicate {

  private String serviceInterface;
  
  ProvidesServicePredicate(String serviceInterface) {
    this.serviceInterface = serviceInterface;
  }
  
  @Override
  boolean matches(Bundle bundle) {
    for (ServiceReference sr : referencesFor(bundle.getBundleContext(), serviceInterface)) {
      if (bundle.equals(sr.getBundle()))
        return true;
    }
    return false;
  }

}
