
package com.ava.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author
 * @CreateDate 2010-8-3
 * @version 1.0.01
 */
@ContextConfiguration(locations={"classpath*:applicationContext.xml", "classpath*:applicationContext-*.xml", "classpath*:spring-*.xml"})
public abstract class TestBase  extends AbstractJUnit4SpringContextTests {

}
