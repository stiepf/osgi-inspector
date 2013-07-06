package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.ServiceReference;

public interface ServicePredicate {

  boolean matches(ServiceReference reference);

}
