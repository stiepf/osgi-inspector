package com.github.stiepf.inspector.components.internal;

import org.osgi.framework.ServiceReference;

public interface ServiceReferencePredicate {

  boolean matches(ServiceReference reference);

}
