package com.github.stiepf.inspector.components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;

public class ContainerDescription {

  private String symbolicName;

  private long bundleId;

  private List<ComponentDescription> componentDescriptions = new LinkedList<ComponentDescription>();

  public ContainerDescription(Bundle bundle) {
    this.symbolicName = bundle.getSymbolicName();
    this.bundleId = bundle.getBundleId();
  }

  public String getSymbolicName() {
    return symbolicName;
  }

  public long getBundleId() {
    return bundleId;
  }

  public List<ComponentDescription> getComponentDescriptions() {
    return Collections.unmodifiableList(componentDescriptions);
  }

  public void addComponentDescription(ComponentDescription description) {
    componentDescriptions.add(description);
  }
}
