#

下面是範例 XML，完整定義參考 schema\grid.xsd
``` xml
<layout>
	<grid name="employee" fontSize="12">
		<columns>
			<column width="100" bind="id"><text>編號</text></column>
			<column width="150" bind="name" wrap="true"><text>姓名</text></column>
			<column width="120" bind="birthday" alignment="center"><text>生日</text></column>
			<column width="+100%" bind="department"><text>所屬部門</text></column>
		</columns>
	</grid>
    <grid name="project">
		<columns fontSize="11" height="20">
			<column width="100" bind="projectName" wrap="true"><text>專案名稱</text></column>
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

## grid
### - elements
* columns - 描述 Grid 的 Header 與 Column(s)。
### - attributes
* name - Grid 的名稱。
* fontSize - Grid 內容字體大小。預設為 9。

## columns
### - elements
* column - 描述 Grid 的 Column。column 的數量可以一到多個。

### - attributes
* background - Header 背景色。預設 212,212,212。
* height - Header 高度。預設 13。
* visible - Header 是否顯示。預設 true。

## column
### - elements
* text - Header 顯示的文字。
### - attributes
* aligement - 左右對齊方式。NEAR、CENTER、FAR。預設為 NEAR。
* background - 被景色。
* bind - 映射資料屬性名稱 (必要條件)。
* width - 欄位寬度 (必要條件)。
* wrap - 文字內容是否自動換行。預設為 false。
