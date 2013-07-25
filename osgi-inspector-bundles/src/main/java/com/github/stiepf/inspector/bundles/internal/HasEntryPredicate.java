package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;

class HasEntryPredicate extends BundlePredicate {

  private String path;
  
  HasEntryPredicate(String path) {
    this.path = path;
  }

  @Override
  boolean matches(Bundle bundle) {
    return bundle.getEntry(path) != null;
  }

}
