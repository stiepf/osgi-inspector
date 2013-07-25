package com.github.stiepf.inspector.bundles.internal;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

class ConsumesServicePredicate extends ServicePredicate {

  private String serviceInterface;
  
  ConsumesServicePredicate(String serviceInterface) {
    this.serviceInterface = serviceInterface;
  }
  
  @Override
  boolean matches(Bundle bundle) {
    ServiceReference[] inUse = bundle.getServicesInUse();
    List<ServiceReference> available = referencesFor(bundle.getBundleContext(), serviceInterface);
    if (inUse != null) {
      for (ServiceReference sr : inUse) {
        if (available.contains(sr))
          return true;
      }
    }
    return false;
  }

}
