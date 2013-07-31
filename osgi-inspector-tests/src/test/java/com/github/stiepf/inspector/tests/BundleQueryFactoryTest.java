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
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.bundles.BundleQuery;
import com.github.stiepf.inspector.bundles.BundleQueryFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class BundleQueryFactoryTest {

  @Inject
  private BundleQueryFactory underTest;
  
  @Configuration
  public Option[] configure() {
    return options(
        mavenBundle("com.github.stiepf.inspector", "osgi-inspector-bundles"),
        junitBundles()
    );
  }
  
  @Test
  public void exportsPackage() throws Exception {
    assertQuery(1, bundleQuery().exportsPackage("com.github.stiepf.inspector.bundles"));
  }
  
  @Test
  public void importsPackage() throws Exception {
    assertQuery(1, bundleQuery().importsPackage("com.github.stiepf.inspector.bundles"));
  }  
  
  @Test
  public void providesService() throws Exception {
    assertQuery(1, bundleQuery().providesService(BundleQueryFactory.class.getName()));
  }
  
  @Test
  public void consumesService() throws Exception {
    assertQuery(1, bundleQuery().consumesService(PackageAdmin.class.getName()));
  }  
  
  @Test
  public void hasEntry() throws Exception {
    assertQuery(1, bundleQuery().hasEntry("com/github/stiepf/inspector/bundles/BundleQuery.class"));
  }
  
  @Test
  public void hasHeader() throws Exception {
    assertQuery(1, bundleQuery().hasHeader("Bundle-Name", "Stiepf::OSGi-Inspector::Bundles"));
  }
  
  private BundleQuery bundleQuery() {
    return underTest.createBundleQuery();
  }
  
  private void assertQuery(int expected, BundleQuery query) {
    List<Bundle> result = query.execute();
    assertEquals(expected, result.size());
  }
}
