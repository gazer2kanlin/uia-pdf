/*
 * Copyright 2015 uia.pdf
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

public class CoordUtils {

    public static int size(String sizeStr, int fullSize, int startPoint) {
    	if (sizeStr.endsWith("%")) {
            if (sizeStr.startsWith("+")) {
            	// +20%, 100, 15 = (100 - 15) * 0.2
                int p = Integer.parseInt(sizeStr.substring(1, sizeStr.length() - 1));
                return (fullSize - startPoint) * p / 100;
            }
            else if (sizeStr.startsWith("@")) {
            	// @20%, 100, 15 = 100 * 0.2 - 15
                int p = Integer.parseInt(sizeStr.substring(1, sizeStr.length() - 1));
                return fullSize * p / 100 - startPoint;
            }
            else {
            	// 20%, 100, 15 = 100 * 0.2
                int p = Integer.parseInt(sizeStr.substring(0, sizeStr.length() - 1));
                return fullSize * p / 100;
            }
        }

    	// 20, 100, 15 = 20
        return Integer.parseInt(sizeStr);
    }

    public static int point(String ptStr, int fullSize, int startPoint) {
    	if(ptStr.startsWith("+")) {
    		return startPoint + Integer.parseInt(ptStr.substring(1));
    	}
    	else {
    		return Integer.parseInt(ptStr);
    	}
    }
}
