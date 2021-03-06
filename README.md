Boilit Script Language
===
<pre>

Bsl全名为Boilit Script Language，是一款主要面向模板引擎方向的脚本语言。

软件作者：Boilit(于景洋)
所在单位：胜利油田胜利软件有限责任公司

开发语言：Java
目前版本：1.2.0
类库大小：241K
引擎性能：超越目前主流及非主流模板引擎，速度一流，适合大中型项目应用，请查看在线文档或基准测试内的测试结果

软件特性：请参考<a href="http://boilit.github.io/bsl">在线文档</a>

在线文档：<a href="http://boilit.github.io/bsl">http://boilit.github.io/bsl</a>

基准测试：<a href="https://github.com/boilit/ebm">https://github.com/boilit/ebm</a>

下载地址: <a href="http://boilit.github.io/bsl/releases/bsl-1.2.0.jar">bsl-1.2.0.jar</a>
    
交流QQ群：109365467
</pre>

版本更新
===
<pre>
1.2.0:
    修改专用编码器多线程并发BUG（空指针异常）；
1.1.0:
    修改运算单元算法；
    完善错误定位；
    移除Logger适配器接口，改为异常抛出；
    修订loop、next、break的检测机制；
    修改Include，参数可接收一个或两个表达式；
    增强UTF-8专用编码器，由UCS-2支持扩展到UCS-4支持
    修改IResource、IResourceLoader接口及缺省实现；
    增加StringResource、StringResourceLoader资源读取方式；
    静态文本处理接口ITextCompressor更改为ITextProcessor，提供缺省实现，一般用不到该功能；
1.0.2:
    更新字符缓冲实现，转义字符BUG修复；
1.0.1：
    更新专用GBKEncoder；
    更新静态文本输出方法及IO部分IPrinter接口的实现；
1.0.0-SNAPHSOT：
    初始发布版本
</pre>

License(许可证)
===
<pre>
Boilit Script Language is released under the MIT License. 
See the bundled LICENSE file for details.

Boilit Script Language依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>
