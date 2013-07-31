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

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static com.github.stiepf.inspector.tests.Constants.*;

import java.util.List;

import javax.inject.Inject;

import org.apache.aries.blueprint.BeanProcessor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentQuery;
import com.github.stiepf.inspector.components.ComponentQueryFactory;
import com.github.stiepf.inspector.components.ContainerDescription;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ComponentQueryFactoryTest {
  
  @Inject
  private ComponentQueryFactory  underTest;
  
  @Inject
  private PackageAdmin packageAdmin;
  
  @Configuration
  public Option[] configure() throws Exception {
    return options(
        mavenBundle("org.apache.aries", "org.apache.aries.util", "1.1.0"),
        mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy", "1.0.1"),
        mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint", "1.1.0"),
        mavenBundle(GROUP_ID, BUNDLES_ARTEFACT_ID, CURRENT_VERSION),
        mavenBundle(GROUP_ID, SERVICES_ARTEFACT_ID, CURRENT_VERSION),
        mavenBundle(GROUP_ID, COMPONENTS_ARTEFACT_ID, CURRENT_VERSION),
        junitBundles()
    );
  }
  
  @BeforeClass
  public static void waitForServices() throws Exception {
    Thread.sleep(100);
  }
  
  @Test
  public void bundleId() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(12, underTest.createComponentQuery().bundleId(blueprint.getBundleId()));
  }
  
  @Test
  public void symbolicName() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(12, underTest.createComponentQuery().symbolicName(blueprint.getSymbolicName()));
  }  
  
  @Test
  public void service() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(6, underTest.createComponentQuery().bundleId(blueprint.getBundleId()).service());
  }
  
  @Test
  public void bean() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(2, underTest.createComponentQuery().bundleId(blueprint.getBundleId()).bean());
  }
  
  @Test
  public void componentId() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(1, underTest.createComponentQuery().bundleId(blueprint.getBundleId()).componentId("blueprintContainer"));
  }
  
  @Test
  public void references() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertTrue(underTest.createComponentQuery().bundleId(blueprint.getBundleId()).reference().execute().isEmpty());
  }  
  
  @Test
  public void type() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    assertQuery(6, underTest.createComponentQuery().bundleId(blueprint.getBundleId()).type("org.apache.aries.blueprint.NamespaceHandler"));
  }
  
  @Test
  public void combined() throws Exception {
    Bundle blueprint = packageAdmin.getBundle(BeanProcessor.class);
    ComponentQuery componentQuery = underTest.createComponentQuery()
        .bundleId(blueprint.getBundleId())
        .type("org.apache.aries.blueprint.NamespaceHandler")
        .service()
        .componentId("component-1");
    assertQuery(1, componentQuery);
  }
  
  private void assertQuery(int expected, ComponentQuery query) {
    List<ContainerDescription> cds = query.execute();
    // assuming a single blueprint container
    List<ComponentDescription> result = cds.get(0).getComponentDescriptions();
    assertEquals(expected, result.size());
  }
}
