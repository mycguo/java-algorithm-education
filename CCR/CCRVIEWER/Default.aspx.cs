using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml.Xsl;
using System.Xml.XPath;
using System.Xml;
using System.IO;


public partial class _Default : System.Web.UI.Page 
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btnUpload_Click(object sender, EventArgs e)
    {
        try
        {
            // Get the HttpFileCollection
            HttpFileCollection hfc = Request.Files;
            for (int i = 0; i < hfc.Count; i++)
            {
                HttpPostedFile hpf = hfc[i];
                if (hpf.ContentLength > 0)
                {
                    string strFile=Server.MapPath("MyFiles") + "\\" +System.IO.Path.GetFileName(hpf.FileName);
                    hpf.SaveAs(strFile);
                    bool r=ConvertToHTML(strFile);                    
                    r = CCDtoHTML(strFile);                    
                    File.Delete(strFile);
                    upstatus.Text = "<b>File: </b>" + hpf.FileName + "  <b>Size:</b> " + hpf.ContentLength + "  <b>Type:</b> " + hpf.ContentType + " Uploaded Successfully <br/>";
                    
                }
            }
        }
        catch (Exception ex)
        {
            
        }
    }

    public bool ConvertToHTML(string inFile)
    {
        string path = this.Server.MapPath(@"XSLT");
        string respath = this.Server.MapPath(@"Results");
        string xsltLocation = path + "\\ccr_formatdate.xsl";
        bool isValid;
        try
        {
            
           // XslCompiledTransform transform = new XslCompiledTransform();
            //transform.Load(xsltLocation);          
            Random rdNUm = new Random();
            string rFile = rdNUm.Next(1000) + ".html";
            //transform.Transform(inFile, respath + "\\" + rFile);
            XslTransform xslt = new XslTransform();
            xslt.Load(xsltLocation);
            XPathDocument xDoc = new XPathDocument(inFile);
            XmlTextWriter writer = new XmlTextWriter(respath+"\\"+rFile, null);
            xslt.Transform(xDoc, null, writer, new XmlUrlResolver());
            writer.Close();
            Session["resultFile"] = "Results/" + rFile; 
            return true;
        }
        catch (Exception ex)
        {
            isValid = false;
            return isValid;
        }

    }

    
    public bool CCDtoHTML(string inFile)
    {
        string path = this.Server.MapPath(@"XSLT");
        string respath = this.Server.MapPath(@"Results");
        string xsltLocation = path + "\\ODOSCCD.xsl";
        bool isValid;
        try
        {
            
           // XslCompiledTransform transform = new XslCompiledTransform();
            //transform.Load(xsltLocation);          
            Random rdNUm = new Random();
            string rFile = rdNUm.Next(1000) + ".html";
            //transform.Transform(inFile, respath + "\\" + rFile);
            XslTransform xslt = new XslTransform();
            xslt.Load(xsltLocation);
            XPathDocument xDoc = new XPathDocument(inFile);
            XmlTextWriter writer = new XmlTextWriter(respath+"\\"+rFile, null);
            xslt.Transform(xDoc, null, writer, new XmlUrlResolver());
            writer.Close();
            Session["CCDFile"] = "Results/" + rFile; 
            return true;
        }
        catch (Exception ex)
        {
            isValid = false;
            return isValid;
        }

    }

    protected void btnConvert_Click(object sender, EventArgs e)
    {
        string fn = Convert.ToString(Session["resultFile"]);
        if (fn != "")
            lblResult.Text = "<iframe id=\"uploadFrame\" frameborder=\"0\" width=\"800\" height=\"800\" scrolling=\"yes\" src=\"" + fn + "\"></iframe>";
        else
            lblResult.Text = "No file uploaded";
    }



    protected void btnConvertCCD_Click(object sender, EventArgs e)
    {
        string fn = Convert.ToString(Session["CCDFile"]);
        if (fn != "")
            lblResult.Text = "<iframe id=\"uploadFrame\" frameborder=\"0\" width=\"800\" height=\"800\" scrolling=\"yes\" src=\"" + fn + "\"></iframe>";
        else
            lblResult.Text = "No file uploaded";
    }
}
