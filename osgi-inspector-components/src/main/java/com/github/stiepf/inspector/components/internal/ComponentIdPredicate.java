package com.github.stiepf.inspector.components.internal;

import com.github.stiepf.inspector.components.ComponentDescription;

class ComponentIdPredicate extends ComponentPredicate {

  private final String id;
  
  ComponentIdPredicate(String id) {
    this.id = id;
  }

  @Override
  boolean matches(ComponentDescription componentDescription) {
    String componentId = componentDescription.getId();
    if (componentId != null) {
      return componentId.toLowerCase().contains(id.toLowerCase());
    } else {
      return false;
    }
  }

}
