/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.engine.test.api.authorization.externaltask;

import org.camunda.bpm.engine.externaltask.ExternalTask;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Thorben Lindhauer
 * @author Christopher Zell
 */
@RunWith(Parameterized.class)
public class HandleExternalTaskFailureAuthorizationTest extends HandleExternalTaskAuthorizationTest {

  @Override
  public void testExternalTaskApi(LockedExternalTask task) {
    engineRule.getExternalTaskService().handleFailure(task.getId(), "workerId", "error", 5, 5000L);
  }

  @Override
  public void assertExternalTaskResults() {
    ExternalTask externalTask = engineRule.getExternalTaskService()
      .createExternalTaskQuery().singleResult();

    Assert.assertEquals(5, (int) externalTask.getRetries());
    Assert.assertEquals("error", externalTask.getErrorMessage());
  }
}
