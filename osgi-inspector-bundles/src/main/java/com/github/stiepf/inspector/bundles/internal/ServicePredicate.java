package com.github.stiepf.inspector.bundles.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

abstract class ServicePredicate extends BundlePredicate {

  protected final List<ServiceReference> referencesFor(BundleContext bundleContext, String serviceInterface) {
    List<ServiceReference> result = Collections.emptyList();
    try {
      ServiceReference[] serviceReferences = bundleContext.getServiceReferences(serviceInterface, null);
      if (serviceReferences != null) {
        result = Arrays.asList(serviceReferences);
      }
    } catch (InvalidSyntaxException e) {
      // Invalid syntax impossible for null argument
    }
    return result;
  }  
  
}
