package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;

class InStatePredicate extends BundlePredicate {

  private int bundleState;
  
  InStatePredicate(int bundleState) {
    this.bundleState = bundleState;
  }

  @Override
  boolean matches(Bundle bundle) {
    return bundleState == bundle.getState();  }

}
