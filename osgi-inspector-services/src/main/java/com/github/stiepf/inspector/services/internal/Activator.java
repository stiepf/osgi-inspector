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
package com.github.stiepf.inspector.services.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.stiepf.inspector.services.ServiceQueryFactory;

public class Activator implements BundleActivator {

  private ServiceRegistration factoryRegistration;

  @Override
  public void start(BundleContext context) throws Exception {
    ServiceQueryFactory factory = new ServiceQueryFactoryImpl(context);
    Dictionary<String, String> properties = new Hashtable<String, String>();
    properties.put("name", "ServiceQueryService");
    factoryRegistration = context.registerService(ServiceQueryFactory.class.getName(), factory, properties);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    factoryRegistration.unregister();
  }

}
