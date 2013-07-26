package com.github.stiepf.inspector.components.internal;

import org.osgi.framework.ServiceReference;

class BundleIdPredicate extends ServiceReferencePredicate {

  private final Long bundleId;
  
  BundleIdPredicate(Long bundleId) {
    this.bundleId = bundleId;
  }

  @Override
  boolean matches(ServiceReference reference) {
    return bundleId.equals(reference.getBundle().getBundleId());
  }

}
