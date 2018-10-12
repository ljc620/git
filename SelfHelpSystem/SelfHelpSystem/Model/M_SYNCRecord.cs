using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SelfHelpSystem.Model
{
    //同步版本表
    class M_SYNCRecord
    {
        private String _snyc_name;
        private DateTime _version_no;

        //表名
        public String Snyc_name
        {
            get { return _snyc_name; }
            set { _snyc_name = value; }
        }
        
        //版本号
        public DateTime Version_no
        {
            get { return _version_no; }
            set { _version_no = value; }
        }

    }
}
