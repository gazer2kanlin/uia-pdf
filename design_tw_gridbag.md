GridBagView
===

## 設計
布局配置範例：
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<layout xmlns="http://gridbag.pdf.uia/layout">
	<gridbag name="title" x="0" y="0" width="100%" height="30" borderEnabled="false">
		<columns>
			<column width="100%"></column>
		</columns>
		<rows>
			<row height="100%">
				<bindCell fontSize="20">
					<bind>titleInfo</bind>
				</bindCell>
			</row>
		</rows>
	</gridbag>
	<gridbag name="header" x="0" y="+" width="100%" height="170">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@70%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="30">
				<textCell>
					<text>產品名稱</text>
					<subtext>Product Name</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell>
					<text>方法</text>
					<subtext>Method</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="30" fontSize="12">
				<textCell>
					<text>P/N 號</text>
					<subtext>P/N No.</subtext>
				</textCell>
				<textCell borderSize="1.0" borderColor="0,0,255"></textCell>
				<textCell>
					<text>批號</text>
					<subtext>Lot ID</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="30">
				<textCell>
					<text>生產效期</text>
					<subtext>Production Date</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell fontSize="12">
					<text>效期</text>
					<subtext>Expiry Date</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="30" background="128,128,255">
				<textCell>
					<text>包裝規格</text>
					<subtext>Package Size</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell background="255,0,0">
					<text>銷售</text>
					<subtext>Selling Kit</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="+100%">
				<textCell>
					<text>檢驗依據</text>
					<subtext>Based On</subtext>
				</textCell>
				<bindCell colspan="3"><bind></bind></bindCell>
			</row>
		</rows>
	</gridbag>
	<gridbag name="inspection" x="0" y="+" width="100%" height="150">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@55%"></column>
			<column width="@70%"></column>
			<column width="@90%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="25">
				<textCell>
					<text>檢驗項目</text>
					<subtext>Items</subtext>
				</textCell>
				<textCell colspan="3">
					<text>檢驗標準</text>
					<subtext>Standard</subtext>
				</textCell>
				<textCell>
					<text>檢驗結果</text>
					<subtext>Results</subtext>
				</textCell>
				<textCell>
					<text>評價</text>
					<subtext>Comment</subtext>
				</textCell>
			</row>
			<row height="25">
				<textCell rowspan="2">
					<text>淨含量</text>
					<subtext></subtext>
				</textCell>
				<bindCell colspan="5"><bind></bind></bindCell>
			</row>
			<row height="25">
				<bindCell colspan="5"><bind></bind></bindCell>
			</row>
			<row height="25">
				<textCell rowspan="2">
					<text>線性圍範</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="3"></textCell>
				<bindCell><bind></bind></bindCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="25">
				<textCell colspan="3"></textCell>
				<bindCell><bind></bind></bindCell>
				<bindCell><bind></bind></bindCell>
			</row>
			<row height="25">
				<textCell>
					<text>準確度</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="3"></textCell>
				<bindCell><bind></bind></bindCell>
				<bindCell><bind></bind></bindCell>
			</row>
		</rows>
	</gridbag>
	<gridbag
			name="result"
			 x="0" y="+"
			 width="100%"
			 height="80"
			 background="210,210,210"
			 bind="result">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@55%"></column>
			<column width="@70%"></column>
			<column width="+50%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="30">
				<textCell>
					<text>結論</text>
					<subtext></subtext>
				</textCell>
				<bindCell colspan="5"><bind></bind></bindCell>
			</row>
			<row height="25">
				<textCell valignment="NEAR">
					<text>檢驗人</text>
					<subtext>QC</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell>
					<text foreground="0,0,255">審核人</text>
					<subtext foreground="100,100,255">Reviewed By</subtext>
				</textCell>
				<bindCell background="255,255,255"><bind></bind></bindCell>
				<textCell valignment="FAR">
					<text>批准人</text>
					<subtext>Approval By</subtext>
				</textCell>
				<bindCell><bind>approver</bind></bindCell>
			</row>
			<row height="+100%">
				<textCell alignment="NEAR">
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell>
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
				<textCell alignment="FAR">
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<bindCell><bind></bind></bindCell>
			</row>
		</rows>
	</gridbag>
</layout>
```

## 說明

### <gridbag\>
||Element|Description|multiple|
|:--:|--|--|--|
|v|columns|Column 集合|1|
|v|rows|Row 集合|1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|name|string|名稱|-|
|v|y|string|X 座標|-|
|v|y|string|Y 座標|-|
|v|width|string|寬|-|
|v|height|string|高|-|
||borderEnabled|boolean|是否顯示邊框|true|
||borderSize|float|邊框寬度|1.0|
||borderColor|string|邊框顏色 (R,G,B)|0,0,0|
||bind|string|資料聯繫名稱|-|
||background|string|背景色|-|
||fontSize|int|字型大小|9|

### <columns\>
||Element|Description|multiple|
|:--:|--|--|--|
|v|column|Column|1..*|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|width|string|欄寬|-|
||background|string|背景色|-|


### <rows\>
||Element|Description|multiple|
|:--:|--|--|--|
|v|row|Row|1..*|

### <row\>
||Element|Description|multiple|
|:--:|--|--|--|
|v|textCell|文字內容|1..*|
|v|bindCell|資料聯繫內容|1..*|
|v|imageCell|影像內容|1..*|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|height|string|欄寬|-|
||borderSize|float|邊框寬度|0.5|
||borderColor|string|邊框顏色 (R,G,B)|-|
||background|string|背景色|-|
||fontSize|int|字型大小|-|
||alignment|string|水平對齊方式|CENTER|
||valignment|string|垂直對齊方式|CENTER|

### <textCell\>
||Element|Description|multiple|
|:--:|--|--|--|
||text|主要內容|0..1|
||subtext|次要內容|0..1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
||colspan|int|跨欄數|1|
||rowspan|int|跨行數|1|
||borderSize|float|邊框寬度|0.5|
||borderColor|string|邊框顏色 (R,G,B)|-|
||background|string|背景色|-|
||fontSize|int|字型大小|-|
||alignment|string|水平對齊方式|CENTER|
||valignment|string|垂直對齊方式|CENTER|


### <bindCell\>
||Element|Description|multiple|
|:--:|--|--|--|
|v|bind|資料聯繫名稱|1|
||renderer|資料渲染介面|1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
||colspan|int|跨欄數|1|
||rowspan|int|跨行數|1|
||borderSize|float|邊框寬度|0.5|
||borderColor|string|邊框顏色 (R,G,B)|-|
||background|string|背景色|-|
||fontSize|int|字型大小|-|
||alignment|string|水平對齊方式|CENTER|
||valignment|string|垂直對齊方式|CENTER|


### <imageCell\>
||Element|Description|multiple|
|:--:|--|--|--|
||url|檔案位置|1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
||colspan|int|跨欄數|1|
||rowspan|int|跨行數|1|
||borderSize|float|邊框寬度|0.5|
||borderColor|string|邊框顏色 (R,G,B)|-|
||background|string|背景色|-|
||fontSize|int|字型大小|-|
||alignment|string|水平對齊方式|CENTER|
||valignment|string|垂直對齊方式|CENTER|
