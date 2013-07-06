package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;

public interface BundlePredicate {

  boolean matches(Bundle bundle);

}
