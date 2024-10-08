/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
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
 * #L%
 */
package io.wcm.sling.models.injectors.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.AnnotatedElement;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.xss.XSSAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.day.cq.i18n.I18n;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.AuthoringUIMode;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;

import io.wcm.sling.commons.request.RequestContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AemObjectInjectorPageTest {

  private final AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

  @Mock
  private AnnotatedElement annotatedElement;
  @Mock
  private Page page;
  @Mock
  private Resource resource;
  @Mock
  private ResourceResolver resourceResolver;
  @Mock
  private PageManager pageManager;
  @Mock
  private Page resourcePage;
  @Mock
  private Designer designer;
  @Mock
  protected TagManager tagManager;
  @Mock
  private Design design;
  @Mock
  protected RequestContext requestContext;

  private AemObjectInjector injector;

  @BeforeEach
  @SuppressWarnings("null")
  void setUp() {
    context.registerService(RequestContext.class, requestContext);
    context.registerInjectActivateService(new ModelsImplConfiguration(),
        "requestThreadLocal", true);

    injector = context.registerInjectActivateService(new AemObjectInjector());

    when(page.adaptTo(Resource.class)).thenReturn(resource);
    when(resource.getResourceResolver()).thenReturn(resourceResolver);
    when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    when(resourceResolver.adaptTo(Designer.class)).thenReturn(designer);
    when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    when(pageManager.getContainingPage(resource)).thenReturn(resourcePage);
    when(designer.getDesign(any(Page.class))).thenReturn(design);
  }

  @Test
  void testPageManager() {
    Object result = injector.getValue(page, null, PageManager.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(pageManager, result);
  }

  @Test
  void testCurrentPage() {
    Object result = injector.getValue(page, null, Page.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(resourcePage, result);
  }

  @Test
  void testResourcePage() {
    Object result = injector.getValue(page, "resourcePage", Page.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(resourcePage, result);
  }

  @Test
  void testWcmMode() {
    Object result = injector.getValue(page, null, WCMMode.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testAuthoringUiMode() {
    Object result = injector.getValue(page, null, AuthoringUIMode.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testComponentContext() {
    Object result = injector.getValue(page, null, ComponentContext.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testDesigner() {
    Object result = injector.getValue(page, null, Designer.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(designer, result);
  }

  @Test
  void testTagManager() {
    Object result = injector.getValue(page, null, TagManager.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(tagManager, result);
  }

  @Test
  void testDesign() {
    Object result = injector.getValue(page, null, Design.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertSame(design, result);
  }

  @Test
  void testStyle() {
    Object result = injector.getValue(page, null, Style.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testXssApi() {
    Object result = injector.getValue(page, null, XSSAPI.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testI18n() {
    Object result = injector.getValue(page, null, I18n.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

  @Test
  void testInvalid() {
    Object result = injector.getValue(this, null, PageManager.class, annotatedElement, mock(DisposalCallbackRegistry.class));
    assertNull(result);
  }

}
