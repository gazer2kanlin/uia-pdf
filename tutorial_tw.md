# 範例一

參考：testTutorial1() @ uia.pdf.grid.GridViewTest.java

需求：建立一個職工清單的 PDF 文件，內容包含職工編號、姓名、生日及所屬部門，以 Grid 方式呈現。

## 設計

* id - 職工編號；寬度：100px。
* name - 姓名；寬度：150px。
* birthday - 生日；寬度：120px；位置：置中。
* department - 所屬部門；寬度：到右側邊界。

## 布局配置

```
<?xml version="1.0" encoding="UTF-8"?>
<layout>
	<grid name="employee">
		<columns>
			<column width="100" bind="id"><text>編號</text></column>
			<column width="150" bind="name" wrap="true"><text>姓名</text></column>
			<column width="120" bind="birthday" alignment="center"><text>生日</text></column>
			<column width="+100%" bind="department"><text>所屬部門</text></column>
		</columns>
	</grid>
</layout>
```
## 顯示資料

參考：createSample() @ uia.pdf.grid.Employee.java

顯示資料結構為 List&lt;Map&lt;String, Object>>，Map 中的 Key 值即為 column bind 的值，例如：
```
TreeMap<String, Object> row = new TreeMap<String, Object>();
row.put("id", "Employee ID");
row.put("name", "My Name");
row.put("birthday", new Date());
row.put("department", "Department Name");
```
## Java Code

```
File layout = new File("sample.xml");
GridModelFactory modelFactory = new GridModelFactory(layout);

File font = new File("traditional.ttf");    // pdfbox 內無中文字型，需額外載入。
PDFMaker pdf = new PDFMaker(font);

A4Paper paper = new A4Paper(true);
GridView view = new GridView(
    pdf,
    paper,
    modelFactory.create("employee", paper);
view.draw(prepareSample(), "員工基本資料");

pdf.save(new File("C:\\TEMP\\EMPLOYEE_LIST.PDF"));

```

# 範例二

參考：testTutorial2() @ uia.pdf.grid.GridViewTest.java

需求：延伸範例一，於職工清單後追加專案清單，專案內容包含專案名稱、開始日期、結束日期、地點、專案經理、品保經理與備註。以 Grid 形式橫向 (landscape) 呈現。

## 設計

* projectName - 專案名稱；寬度：100px。
* startDate - 開始日期；寬度：120px；位置：置右。
* endDate - 結束日期；寬度：120px；位置：置右。
* location - 地點；寬度：70px。
* pm - 專案經理；寬度：80px。
* qm - 品保經理；寬度：80px。
* remark - 備註；寬度：到右側邊界。

## 布局配置

```
<?xml version="1.0" encoding="UTF-8"?>
<layout>
	<grid name="employee">
		<columns>
			<column width="100" bind="id"><text>編號</text></column>
			<column width="150" bind="name" wrap="true"><text>姓名</text></column>
			<column width="120" bind="birthday" alignment="center"><text>生日</text></column>
			<column width="+100%" bind="department"><text>所屬部門</text></column>
		</columns>
	</grid>
    <grid name="project">
		<columns>
			<column width="100" bind="projectName"><text>專案名稱</text></column>
			<column width="120" bind="startDate" alignment="FAR"><text>開始日期</text></column>
			<column width="120" bind="endDate" alignment="FAR"><text>結束日期</text></column>
			<column width="70" bind="location"><text>地點</text></column>
			<column width="80" bind="pm"><text>專案經理</text></column>
			<column width="80" bind="qm"><text>品保經理</text></column>
			<column width="+100%" bind="remark"><text>備註</text></column>
		</columns>
	</grid>
</layout>
```
## 顯示資料

參考：createSample() @ uia.pdf.grid.Project.java

## Java Code

```
File layout = new File("sample.xml");
GridModelFactory modelFactory = new GridModelFactory(layout);

File font = new File("traditional.ttf");    // pdfbox 內無中文字型，需額外載入。
PDFMaker pdf = new PDFMaker(font);

A4Paper paper1 = new A4Paper();
GridView view1 = new GridView(
        pdf,
        paper1,
        modelFactory.create("employee", paper1));
view1.draw(Employee.createSample(), "員工基本資料");

A4Paper paper2 = new A4Paper(true); // 橫向 landscape
GridView view2 = new GridView(
        pdf,
        paper2,
        modelFactory.create("project", paper2));
view2.draw(Project.createSample(), "專案基本資料");

pdf.save(new File("C:\\TEMP\\EMPLOYEE_PROJECT_LIST.PDF"));

```
