package com.github.stiepf.inspector.services.internal;

import java.util.Arrays;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

class ConsumedByPredicate extends ServicePredicate {

  private final Bundle bundle;
  
  ConsumedByPredicate(Bundle bundle) {
    this.bundle = bundle;
  }

  @Override
  boolean matches(ServiceReference reference) {
    Bundle[] usingBundles = reference.getUsingBundles();
    if (usingBundles != null) {
      return Arrays.asList(usingBundles).contains(bundle);
    } else {
      return false;
    }
  }

}
