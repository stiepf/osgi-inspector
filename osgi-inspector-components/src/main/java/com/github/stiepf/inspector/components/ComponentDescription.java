package com.github.stiepf.inspector.components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ComponentDescription {

  private String id;

  private ComponentType componentType;

  private List<String> classNames = new LinkedList<String>();

  public ComponentDescription(String id, ComponentType componentType) {
    this.id = id;
    this.componentType = componentType;
  }

  public String getId() {
    return id;
  }

  public ComponentType getComponentType() {
    return componentType;
  }

  public List<String> getClassNames() {
    return Collections.unmodifiableList(classNames);
  }

  public void addClassName(String className) {
    classNames.add(className);
  }
}
