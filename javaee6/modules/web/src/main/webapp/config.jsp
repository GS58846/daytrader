<!--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" bASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!DOCTYPE html PUbLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" 
           xmlns:f="http://java.sun.com/jsf/core" 
           xmlns:h=http://java.sun.com/jsf/html
            xmlns:ui="http://java.sun.com/jsf/facelets">
            
<h:head>Welcome to DayTrader</h:head>          
<h:body bgcolor="#ffffff" link="#000099">
<%@ page import="org.apache.geronimo.daytrader.javaee6.utils.TradeConfig"
session="false" isThreadSafe="true" isErrorPage="false"%>

<table style="font-size: smaller">
<tbody>
<tr>
<td bgcolor="#c93333" align="left" width="640" height="10"><b><FONT
color="#ffffff">DayTrader Configuration</FONT></b></td>
<td align="center" bgcolor="#000000" width="100" height="10"><FONT
color="#ffffff"><b>DayTrader</b></FONT></td>
</tr>
<tr>
<td colspan="6">
<hr>
</td>
</tr>
<tr>
<td colspan="6"></td>
</tr>
</tbody>
</table>

<%
String status;
status = (String) request.getAttribute("status");
if (status != null) {
%>
<table width="740" height="30">
<tbody>
<tr>
<td></td>
<td><FONT color="#ff0033"><% out.print(status); %> </FONT></td>
<td></td>
</tr>
</tbody>
</table>
<%
}
%>

<h:form action="config" method="POST">
<h:inputHidden id="name" value="updateConfig"></h:inputHidden>
<!--<INPUT type="hidden" name="action"
value="updateConfig">-->

<table border="1" width="740">
<tbody>
<tr>
<td colspan="2">The current DayTrader runtime configuration is
detailed below. View and optionally update run-time parameters.
&nbsp;<br>
<br>
<b>NOTE: </b>Parameters settings will return to default
on&nbsp;server restart. To make configuration settings persistent
across application server stop/starts, edit the servlet init
parameters for each DayTrader servlet. This is described in the <A
href="docs/tradeFAQ.html">DayTrader FAQ</A>.<br>
<hr>
</td>
</tr>
<tr>
<td align="left"><b>Run-Time Mode </b>
<P align="left"><%String configParm = "RunTimeMode";
String names[] = TradeConfig.runTimeModeNames;
int index = TradeConfig.runTimeMode;
for (int i = 0; i < names.length; i++) {
out.print(
"<INPUT type=\"radio\" name=\""
+ configParm
+ "\" value=\""
+ i
+ "\" ");
if (index == i)
out.print("checked");
out.print("> " + names[i] + "<br>");
}
%></P>
</td>
<td><br>
Run Time Mode determines server implementation of the TradeServices
to use in the DayTrader application Enterprise Java beans including
Session, Entity and Message beans or Direct mode which uses direct
database and JMS access. See <A href="docs/tradeFAQ.html">DayTrader
FAQ</A> for details.<br>
</td>
</tr>

<tr>
<td align="left"><b>Scenario Workload Mix</b>
<P align="left"><%configParm = "WorkloadMix";
names = TradeConfig.workloadMixNames;
index = TradeConfig.workloadMix;
for (int i = 0; i < names.length; i++) {
out.print(
"<INPUT type=\"radio\" name=\""
+ configParm
+ "\" value=\""
+ i
+ "\" ");
if (index == i)
out.print("checked");
out.print("> " + names[i] + "<br>");
}
%></P>
</td>
<td>This setting determines the runtime workload mix of DayTrader
operations when driving the benchmark through TradeScenarioServlet.
See <a href="docs/tradeFAQ.html">DayTrader FAQ</a> for details.</td>
</tr>
<tr>
<td align="left"><b>WebInterface</b>
<P align="left"><%configParm = "WebInterface";
names = TradeConfig.webInterfaceNames;
index = TradeConfig.webInterface;
for (int i = 0; i < names.length; i++) {
out.print(
"<INPUT type=\"radio\" name=\""
+ configParm
+ "\" value=\""
+ i
+ "\" ");
if (index == i)
out.print("checked");
out.print("> " + names[i] + "<br>");
}
%></P>
</td>
<td>This setting determines the Web interface technology used, JSPs
or JSPs with static images and GIFs.</td>
</tr>
<!--<tr>
<td align="left">
<b>Caching Type</b>
<P align="left"><%configParm = "CachingType";
names = TradeConfig.cachingTypeNames;
index = TradeConfig.cachingType;
for (int i = 0; i < names.length; i++) {
out.print(
"<INPUT type=\"radio\" name=\""
+ configParm
+ "\" value=\""
+ i
+ "\" ");
if (index == i)
out.print("checked");
out.print("> " + names[i] + "<br>");
}
%></P>
</td>
<td>
This setting determines the caching technology used for data caching
, DistributedMap, Command Caching or No Caching.
</td>
</tr>-->
<tr>
<td colspan="2" align="center"><b>Miscellaneous Settings</b></td>
</tr>
<tr>
<td align="left"><b>DayTrader Max Users </b><br>
<h:inputText size="25" label="MaxUsers" value="#{TradeConfig.getMAX_USERS}">
<!--<INPUT size="25" type="text" name="MaxUsers"
value="<%=TradeConfig.getMAX_USERS()%>">-->
<br>
<b>Trade Max Quotes</b><br>
<h:inputText size="25" label="MaxQuotes" value="#{TradeConfig.getMAX_QUOTES}">
<!--<INPUT size="25" type="text" name="MaxQuotes"
value="<%=TradeConfig.getMAX_QUOTES()%>">-->
</td>
<td>by default the DayTrader database is populated with 200 users
(uid:0 - uid:199) and 400 quotes (s:0 - s:399). <br>
</td>
</tr>
<tr>
<td align="left"><b>Market Summary Interval</b><br>
<h:inputText size="25" label="MaxQuotes" value="#{TradeConfig.getMarketSummaryInterval}">
<!--<INPUT size="25" type="text" name="marketSummaryInterval"
value="<%=TradeConfig.getMarketSummaryInterval()%>">-->
</td>
<td>&lt; 0 Do not perform Market Summary Operations.
<br>= 0 Perform market Summary on every request.</br>
<br>&gt; 0 number of seconds between Market Summary Operations</br></td>
</tr>
<tr>
<td align="left"><b>Primitive Iteration</b><br>
<h:inputText size="25" label="MaxQuotes" value="#{TradeConfig.getPrimIterations}">
<!--<INPUT size="25" type="text" name="primIterations"
value="<%=TradeConfig.getPrimIterations()%>">-->

</td>
<td>by default the DayTrader primitives are execute one operation per
web request. Change this value to repeat operations multiple times
per web request.</td>
</tr>

<tr>
<h:inputHidden lable="EnablePublishQuotePriceChange" value=""></h:inputHidden>
<!--<INPUT type="hidden" name="EnablePublishQuotePriceChange" value=""/>-->
</tr>

<tr>
<td align="left">
<!--<h:selectBooleanCheckbox id="EnableLongRun" rendered="true" binding="#{TradeConfig.getLongRun}" />
<h:outputLabel
    for="EnableLongRun"
    rendered="true"
    binding="#{TradeConfig.getLongRunText}">
     <h:outputText
        id="EnableLongRunText"
        value="#{TradeConfig.getLongRun}" />
</h:outputLabel>-->
<INPUT type="checkbox"
                <%=TradeConfig.getLongRun() ? "checked" : ""%>
                name="EnableLongRun">
<b><FONT size="-1">Enable long run support</FONT></b><br>
            </td>
            <td>
                Enable long run support by disabling the show all orders query performed on the Account page.<br>
            </td>
        </tr>
        <tr>
            <td align="left">
            <INPUT type="checkbox"
<%=TradeConfig.getActionTrace() ? "checked" : ""%>
name="EnableActionTrace"> <b><FONT size="-1">Enable operation trace</FONT></b><br>
<INPUT type="checkbox" <%=TradeConfig.getTrace() ? "checked" : ""%>
name="EnableTrace"> <b><FONT size="-1">Enable full trace</FONT></b>
</td>
<td>Enable DayTrader processing trace messages<br>
</td>
</tr>
<tr>
<td colspan="2" align="right">
<!--<INPUT type="submit"
value="Update Config">-->
<h:commandButton value="Submit"
     action="Update Config"/>
<h:
</td>
</tr>
</tbody>
</table>

<table width="740" height="54" style="font-size: smaller">
<tbody>
<tr>
<td colspan="2">
<hr>
</td>
</tr>
<tr>
<td colspan="2"></td>
</tr>
<tr>
<td bgcolor="#c93333" align="left" width="640" height="10"><b><FONT
color="#ffffff">DayTrader Configuration</FONT></b></td>
<td align="center" bgcolor="#000000" width="100" height="10"><FONT
color="#ffffff"><b>DayTrader</b></FONT></td>
</tr>
</tbody>
</table>
</h:form>
</h:body>
</html>
