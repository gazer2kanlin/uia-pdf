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

package uia.pdf.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SampleProject {

    public static List<Map<String, Object>> create() {
        ArrayList<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 100; i++) {
            all.add(createOne(i));
        }
        return all;
    }

    public static Map<String, Object> createOne(int i) {
        TreeMap<String, Object> data = new TreeMap<String, Object>();
        data.put("projectName", "Some Important CIM Project #" + i);
        data.put("startDate", new Date());
        data.put("endDate", new Date());
        data.put("location", "SZ, China");
        data.put("pm", "Ming Wang");
        data.put("qm", "Apple Zhao");
        data.put("remark", "Remark Info #" + i);
        return data;
    }
}
