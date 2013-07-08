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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

import com.github.stiepf.inspector.services.ServiceQuery;
import com.github.stiepf.inspector.services.ServiceQueryFactory;

@Command(scope = "inspector", name = "services", description = "Find services which match certain criteria")
public class FindServicesCommand extends OsgiCommandSupport {

  @Option(name = "--interface", aliases = { "-i" }, description = "implemented service interface")
  private String implementedInterface;

  @Option(name = "--filter", aliases = { "-f" }, description = "LDAP search filter")
  private String filter;

  @Option(name = "--providedBy", aliases = { "-p" }, description = "bundle id of the registering bundle")
  private Long providingBundleId;

  @Option(name = "--usedBy", aliases = { "-u" }, description = "bundle id of the using bundle")
  private Long usingBundleId;

  @Option(name = "--propertyKey", aliases = { "-k" }, description = "service property key")
  private String propertyKey;

  @Option(name = "--propertyKey", aliases = { "-v" }, description = "service property value")
  private String propertyValue;

  private ServiceQueryFactory serviceQueryFactory;

  public void setServiceQueryFactory(ServiceQueryFactory serviceQueryFactory) {
    this.serviceQueryFactory = serviceQueryFactory;
  }

  @Override
  protected Object doExecute() {
    ServiceQuery query = buildServiceQuery();
    printResult(query.list());
    return null;
  }

  private ServiceQuery buildServiceQuery() {
    ServiceQuery result = serviceQueryFactory.createServiceQuery().hasInterface(implementedInterface)
        .matchesFilter(filter);

    if (providingBundleId != null) {
      result.providedBy(getBundle(providingBundleId));
    }

    if (usingBundleId != null) {
      result.consumedBy(getBundle(usingBundleId));
    }

    if (propertyKey != null) {
      result.hasProperty(propertyKey);
    }

    if (propertyValue != null) {
      String[] parts = propertyValue.split(":", 2);
      result.hasPropertyValue(parts[0].trim(), parts[1].trim());
    }
    return result;
  }

  private void printResult(List<ServiceReference> references) {
    PrintStream out = session.getConsole();
    out.println("Service ID  Bundle ID Implemented Interfaces");
    out.println("----------|----------|---------------------------------");
    for (ServiceReference sr : references) {
      out.println(new StringBuilder().append("[").append(getServiceId(sr)).append("]").append("[")
          .append(getBundleId(sr)).append("]").append("[").append(getRegisteredInterfaces(sr)).append("]"));
    }
  }

  private Bundle getBundle(Long bundleId) {
    return bundleContext.getBundle(bundleId.longValue());
  }

  private String getServiceId(ServiceReference reference) {
    return String.format("%9d", reference.getProperty(Constants.SERVICE_ID));
  }

  private String getBundleId(ServiceReference reference) {
    return String.format("%9d", reference.getBundle().getBundleId());
  }

  private String getRegisteredInterfaces(ServiceReference sr) {
    List<String> interfaceNames = Arrays.asList((String[]) sr.getProperty(Constants.OBJECTCLASS));
    Iterator<String> listIt = interfaceNames.iterator();
    StringBuilder buffer = new StringBuilder(listIt.next());
    while (listIt.hasNext()) {
      buffer.append(",").append(listIt.next());
    }
    return buffer.toString();
  }
}
