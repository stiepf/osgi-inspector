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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.Bundle;

import com.github.stiepf.inspector.bundles.BundleQuery;
import com.github.stiepf.inspector.bundles.BundleQueryFactory;

@Command(scope = "inspector", name = "bundles", description = "Find bundles which match certain criteria")
public class FindBundlesCommand extends OsgiCommandSupport {

  private static final Map<Integer, String> BUNDLE_STATES;

  static {
    BUNDLE_STATES = new HashMap<Integer, String>();
    BUNDLE_STATES.put(Bundle.ACTIVE, "Active");
    BUNDLE_STATES.put(Bundle.INSTALLED, "Installed");
    BUNDLE_STATES.put(Bundle.RESOLVED, "Resolved");
    BUNDLE_STATES.put(Bundle.STARTING, "Starting");
    BUNDLE_STATES.put(Bundle.STOPPING, "Stopping");
    BUNDLE_STATES.put(Bundle.UNINSTALLED, "Uninstalled");
  }

  @Option(name = "--exports", aliases = { "-x" }, description = "exported package")
  private String exports;

  @Option(name = "--imports", aliases = { "-i" }, description = "imported package")
  private String imports;

  @Option(name = "--consumes", aliases = { "-c" }, description = "consumed servcice interface")
  private String consumes;

  @Option(name = "--provides", aliases = { "-p" }, description = "provides servcice interface")
  private String provides;

  @Option(name = "--entry", aliases = { "-e" }, description = "has bundle manifest entry")
  private String entry;

  @Option(name = "--header", aliases = { "-h" }, description = "has manifest header value")
  private String header;

  private BundleQueryFactory bundleQueryFactory;

  public void setBundleQueryFactory(BundleQueryFactory bundleQueryFactory) {
    this.bundleQueryFactory = bundleQueryFactory;
  }

  @Override
  protected Object doExecute() {
    BundleQuery bq = buildBundleQuery();
    printResult(bq.list());
    return null;
  }

  private BundleQuery buildBundleQuery() {
    BundleQuery bq = bundleQueryFactory.createBundleQuery();

    if (exports != null) {
      bq.exportsPackage(exports);
    }

    if (imports != null) {
      bq.importsPackage(imports);
    }

    if (consumes != null) {
      bq.consumesService(consumes);
    }

    if (provides != null) {
      bq.providesService(provides);
    }

    if (entry != null) {
      bq.hasEntry(entry);
    }

    if (header != null) {
      String[] parts = header.split(":", 2);
      bq.hasHeader(parts[0].trim(), parts[1].trim());
    }
    return bq;
  }

  private void printResult(List<Bundle> result) {
    PrintStream out = session.getConsole();
    out.println("  ID  State     Bundle (Version)");
    out.println("----|----------|---------------------------------------");
    for (Bundle b : result) {
      out.println(new StringBuilder().append("[").append(String.format("%3d", b.getBundleId())).append("]").append("[")
          .append(String.format("%-8s", BUNDLE_STATES.get(b.getState()))).append("]").append("[")
          .append(b.getHeaders().get("Bundle-Name")).append(" ").append("(").append(b.getVersion()).append(")]"));
    }
  }

}
