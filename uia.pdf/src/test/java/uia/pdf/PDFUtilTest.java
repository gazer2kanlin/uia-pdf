/*
 * Copyright ${year} uia.pdf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uia.pdf;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Assert;
import org.junit.Test;

public class PDFUtilTest {

    @Test
    public void testSplit() throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        DrawingUtils.getContentWrapHeight("1234567890ABCDEFG", PDType1Font.COURIER, 12, 50, result);
        for (String text : result) {
            System.out.println(text);
        }
    }

    @Test
    public void testSizing() {
        Assert.assertEquals(CoordUtils.size("0", 1000, 0), 0);
        Assert.assertEquals(CoordUtils.size("10", 1000, 0), 10);
        Assert.assertEquals(CoordUtils.size("20%", 1000, 10), 200);
        Assert.assertEquals(CoordUtils.size("+50%", 1000, 500), 250);

        /*
         *        20%       40%                 80%      100%
         * .........x.........x...................x.........x
         * 0      200       400                 800       100
         *
         *           .........x...................x.........x
         *           0      25%                 75%      100%
         *                                         .........x
         *                                         0     100%
         */
        Assert.assertEquals(CoordUtils.size("@20%", 1000, 0), 200);
        Assert.assertEquals(CoordUtils.size("+25%", 1000, 200), 200);
        Assert.assertEquals(CoordUtils.size("@80%", 1000, 400), 400);
        Assert.assertEquals(CoordUtils.size("+100%", 1000, 800), 200);
    }
}
