/* TOGGLE ASSETINFO */
var infoIcon = new Image(16,16);
infoIcon .src = "http://qa4.img.fbf.fai.fujitsu.com/common/icons/info.gif";
var rinfoIcon = new Image(16,16);
rinfoIcon.src = "http://qa4.img.fbf.fai.fujitsu.com/common/icons/r-info.gif";


function ToggleAssetInfo(assetinfoId, iconId) {
	var image=document.images?document.images[iconId]:'';
	var assetinfo=
		document.getElementById?document.getElementById(assetinfoId).style:
		document.all?document.all[assetinfoId].style:'';
	if(image&&assetinfo) {
		var s=(assetinfo.display=="none");
		image.src=s?rinfoIcon.src:infoIcon.src;
		assetinfo.display=s?"":"none";
		return false;
	} else return true;
}