GridView
===

## 設計
布局配置範例：
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<layout xmlns="http://grid.pdf.uia/layout">
	<grid name="image">
		<columns height="20" visible="false">
			<column width="100%" bind="imageInfo" cellRenderer="uia.pdf.grid.ImageDescCellRenderer" alignment="CENTER"><text></text></column>
		</columns>
	</grid>
	<grid name="employee">
		<columns visible="true" height="20">
			<column width="100" bind="id" wrap="true"><text>編號</text></column>
			<column width="150" bind="name" wrap="true"><text>姓名</text></column>
			<column width="120" bind="birthday" alignment="CENTER" wrap="true"><text>生日</text></column>
			<column width="@90%" bind="department" wrap="true"><text>所屬部門</text></column>
			<column width="+100%" bind="retired" cellRenderer="uia.pdf.grid.MyBooleanRenderer" alignment="FAR"><text>退休</text></column>
		</columns>
	</grid>
    <grid name="project">
		<columns visible="true" height="20" background="200,255,255">
			<column width="100" bind="projectName" wrap="true"><text>專案名稱</text></column>
			<column width="120" bind="startDate" alignment="CENTER"><text>開始日期</text></column>
			<column width="120" bind="endDate" alignment="CENTER"><text>結束日期</text></column>
			<column width="70" bind="location"><text>地點</text></column>
			<column width="80" bind="pm"><text>專案經理</text></column>
			<column width="80" bind="qm"><text>品保經理</text></column>
			<column width="+100%" bind="remark"><text>備註</text></column>
		</columns>
	</grid>
</layout>
```
## 說明
### <grid\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|columns|欄位集合|1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|name|string|名稱|-|

### <columns\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|column|欄位|1..*|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|height|string|表頭高度|-|
||visible|boolean|表頭是否顯示|true|

### <column\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|text|表頭文字|-|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|width|string|欄寬|-|
|v|bind|string|聯繫欄位|-|
||alignment|string|水平對齊方式 (NEAR, CENTER, FAR)|CENTER|
||wrap|string|自動換行|false|
||cellRenderer|string|資料渲染類別<br> 實作 uia.pdf.grid.CellRenderer 或是<br>繼承 uia.pdf.grid.DefaultCellRenderer|-|
