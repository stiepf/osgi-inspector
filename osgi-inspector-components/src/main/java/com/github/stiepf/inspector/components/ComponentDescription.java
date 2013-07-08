/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
