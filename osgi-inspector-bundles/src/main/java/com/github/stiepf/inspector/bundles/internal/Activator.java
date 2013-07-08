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
package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.stiepf.inspector.bundles.BundleQueryFactory;

public class Activator implements BundleActivator {

  private ServiceRegistration factoryRegistration;

  @Override
  public void start(BundleContext context) throws Exception {
    BundleQueryFactory factory = new BundleQueryFactoryImpl(context);
    factoryRegistration = context.registerService(BundleQueryFactory.class.getName(), factory, null);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    factoryRegistration.unregister();
  }

}
