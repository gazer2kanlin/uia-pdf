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

package uia.pdf.grid.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Employee {

    public static List<Map<String, Object>> createSample() {
        ArrayList<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 100; i++) {
            all.add(createOne(i));
        }
        return all;
    }

    public static Map<String, Object> createOne(int i) {
        TreeMap<String, Object> data = new TreeMap<String, Object>();
        data.put("id", 1001 + i);
        data.put("name", "My Name " + i);
        data.put("birthday", new Date());
        data.put("department", "Research & Design");
        return data;
    }
}
