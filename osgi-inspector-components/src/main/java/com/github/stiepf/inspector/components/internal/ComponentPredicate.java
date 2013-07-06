package com.github.stiepf.inspector.components.internal;

import com.github.stiepf.inspector.components.ComponentDescription;

public interface ComponentPredicate {

  boolean matches(ComponentDescription componentDescription);

}
