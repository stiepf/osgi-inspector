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
