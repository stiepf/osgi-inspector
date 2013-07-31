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
package com.github.stiepf.inspector.tests;

import static com.github.stiepf.inspector.tests.Constants.*;
import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.services.ServiceQuery;
import com.github.stiepf.inspector.services.ServiceQueryFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ServiceQueryFactoryTest {

  @Inject
  private ServiceQueryFactory underTest;
  
  @Inject
  private PackageAdmin packageAdmin;
  
  @Configuration
  public Option[] configure() {
    return options(
        mavenBundle(GROUP_ID, SERVICES_ARTEFACT_ID, CURRENT_VERSION),
        junitBundles()
    );
  }
  
  @Test
  public void providedBy() throws Exception {
    Bundle bundle = packageAdmin.getBundle(ServiceQuery.class);
    assertQuery(1, serviceQuery().providedBy(bundle));
  }
  
  @Test
  public void consumedBy() throws Exception {
    Bundle bundle = packageAdmin.getBundle(ServiceQueryFactoryTest.class);
    assertQuery(4, serviceQuery().consumedBy(bundle));
  }
  
  @Test
  public void hasInterface() throws Exception {
    assertQuery(1, serviceQuery().hasInterface(ServiceQueryFactory.class.getName()));
  }
  
  @Test
  public void hasProperty() throws Exception {
    assertQuery(1, serviceQuery().hasProperty("name"));
  }  
  
  @Test
  public void hasPropertyValue() throws Exception {
    assertQuery(1, serviceQuery().hasPropertyValue("name", "ServiceQueryService"));
  }
  
  @Test
  public void matchesFilter() throws Exception {
    assertQuery(1, serviceQuery().matchesFilter("(name=ServiceQueryService)"));
  }  
  
  private ServiceQuery serviceQuery() {
    return underTest.createServiceQuery();
  }
  
  private void assertQuery(int expected, ServiceQuery query) {
    List<ServiceReference> result = query.execute();
    assertEquals(expected, result.size());
  }
}
