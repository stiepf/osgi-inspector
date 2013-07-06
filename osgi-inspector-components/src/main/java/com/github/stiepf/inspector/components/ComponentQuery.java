package com.github.stiepf.inspector.components;

import java.util.List;

public interface ComponentQuery {

  ComponentQuery componentId(String id);

  ComponentQuery bundleId(Long bundleId);

  ComponentQuery symbolicName(String symbolicName);

  ComponentQuery type(String className);

  ComponentQuery bean();

  ComponentQuery service();

  ComponentQuery reference();

  List<ContainerDescription> list();

}
