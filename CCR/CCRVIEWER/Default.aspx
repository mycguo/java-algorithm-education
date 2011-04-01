<%@ Page Language="C#" AutoEventWireup="true"  CodeFile="Default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Upload Multiple Files in ASP.NET Using jQuery</title>
        <script src="Scripts/jquery-1.3.2.js" type="text/javascript"></script>
        <script src="Scripts/jquery.MultiFile.js" type="text/javascript"></script>
        

<style>
	/* The following styles are used only for this page - the actual plugin styles are in slidernav.css */
	* { margin: 0; padding: 0; }
	body { padding: 40px; background: #eee; font-family: Verdana, Arial; font-size: 12px; line-height: 18px; }
	a { text-decoration: none; }
	h2, h3 { margin: 0 0 20px; text-shadow: 2px 2px #fff; }
	h2 { font-size: 28px; }
	h3 { font-size: 22px; }
	pre { background: #fff; width: 460px; padding: 10px 20px; border-left: 5px solid #ccc; margin: 0 0 20px; }
	p { width: 500px; font-size: 18px; line-height: 24px; margin: 0 0 30px; }
	input { margin: 0; padding: 0; }
	
	.button {
		padding: 5px 10px;
		display: inline;
		background: #777 url(button.png) repeat-x bottom;
		border: none;
		color: #fff;
		cursor: pointer;
		font-weight: bold;
		border-radius: 5px;
		-moz-border-radius: 5px;
		-webkit-border-radius: 5px;
		text-shadow: 1px 1px #666;
		}
	.button:hover {
		background-position: 0 center;
		}
	.button:active {
		background-position: 0 top;
		position: relative;
		top: 1px;
		padding: 6px 10px 4px;
		}
	.button.red { background-color: #e50000; }
	.button.purple { background-color: #9400bf; }
	.button.green { background-color: #58aa00; }
	.button.orange { background-color: #ff9c00; }
	.button.blue { background-color: #2c6da0; }
	.button.black { background-color: #333; }
	.button.white { background-color: #fff; color: #000; text-shadow: 1px 1px #fff; }
	.button.small { font-size: 75%; padding: 3px 7px; }
	.button.small:active { padding: 4px 7px 2px; background-position: 0 top; }
	.button.large { font-size: 125%; padding: 7px 12px; }
	.button.large:active { padding: 8px 12px 6px; background-position: 0 top; }
</style>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <table cellpadding="0"  cellspacing="5" border="0"><tr><td>STEP1: Select file</td><td>
        <asp:FileUpload ID="FileUpload1" runat="server" class="multi"/></td><td>       
        <asp:Button ID="btnUpload" runat="server" Text="Upload" 
            onclick="btnUpload_Click" CssClass="button blue" /></td></tr>
        <tr><td colspan="3"> <asp:Label id="upstatus" runat="server" ></asp:Label></td></tr>
        <tr><td colspan="3">
        STEP2:<asp:Button  CssClass="button blue" ID="Button1" runat="server" Text="CCRTOHTML" 
            onclick="btnConvert_Click" />  
            <asp:Button CssClass="button blue" ID="Button2" runat="server" Text="CCDTOHTML" 
            onclick="btnConvertCCD_Click" />
         </td></tr>
        </table>
      </div>     
      <asp:Label id="lblResult" runat="server"></asp:Label>      
    </form>
</body>
</html>
