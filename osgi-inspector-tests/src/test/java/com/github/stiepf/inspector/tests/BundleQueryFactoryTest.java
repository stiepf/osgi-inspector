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
    List<Bundle> result = query.list();
    assertEquals(expected, result.size());
  }
}
