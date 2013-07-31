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
package com.github.stiepf.inspector.commands.internal;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentQuery;
import com.github.stiepf.inspector.components.ComponentQueryFactory;
import com.github.stiepf.inspector.components.ContainerDescription;

@Command(scope = "inspector", name = "components", description = "Find blueprint components")
public class FindComponentsCommand extends OsgiCommandSupport {

  @Option(name = "--references", aliases = { "-r" }, description = "Show only service references")
  private boolean showReferences;

  @Option(name = "--beans", aliases = { "-b" }, description = "Show only local bean components")
  private boolean showBeans;

  @Option(name = "--services", aliases = { "-s" }, description = "Show only exported services")
  private boolean showServices;

  @Option(name = "--bundleId", aliases = { "-i" }, description = "Show only components from this bundle")
  private Long bundleId;

  @Option(name = "--symbolicName", aliases = { "-n" }, description = "Show only components from bundles with this name")
  private String symbolicName;

  @Option(name = "--type", aliases = { "-t" }, description = "Show only components of this type")
  private String className;

  @Option(name = "--componentId", aliases = { "-c" }, description = "Part of the component id (case-insensitive)")
  private String componentId;

  private ComponentQueryFactory componentQueryFactory;

  public void setComponentQueryFactory(ComponentQueryFactory componentQueryFactory) {
    this.componentQueryFactory = componentQueryFactory;
  }

  @Override
  protected Object doExecute() throws Exception {
    ComponentQuery query = buildBlueprintQuery();
    printResult(query.execute());
    return null;
  }

  private ComponentQuery buildBlueprintQuery() {
    ComponentQuery query = componentQueryFactory.createComponentQuery();

    if (showReferences) {
      query.reference();
    }

    if (showServices) {
      query.service();
    }

    if (showBeans) {
      query.bean();
    }

    if (symbolicName != null) {
      query.symbolicName(symbolicName);
    }

    if (bundleId != null) {
      query.bundleId(bundleId);
    }

    if (className != null) {
      query.type(className);
    }

    if (componentId != null) {
      query.componentId(componentId);
    }
    return query;
  }

  private void printResult(List<ContainerDescription> descriptions) {
    PrintStream out = System.out;

    for (ContainerDescription cd : descriptions) {
      out.println("Components of " + cd.getSymbolicName() + "(" + cd.getBundleId() + ")");
      out.println("--------------------------------------------------------------------");
      for (ComponentDescription compDesc : cd.getComponentDescriptions()) {
        StringBuilder buffer = new StringBuilder(compDesc.getId()).append(" - ").append(compDesc.getComponentType())
            .append(" [");
        Iterator<String> stringIt = compDesc.getClassNames().iterator();
        buffer.append(stringIt.next());
        while (stringIt.hasNext()) {
          buffer.append(",").append(stringIt.next());
        }
        buffer.append("]");
        out.println(buffer);
      }
      out.println();
    }
  }

}
