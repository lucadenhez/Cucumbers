document.addEventListener("DOMContentLoaded", function() {
    const options = {
        borderColor: "#bdbdbd",
        cutout: 75,
        responsive: true,
        backgroundColor: [
            "#5f1fc4",
            "#7859a8",
            "#3d0791",
            "#633d80"
        ],
        plugins: {   
            legend: {
              display: false
            },
        },
    }
    
    const data = {
        labels: [
            "GTA V",
            "Microsoft Teams",
            "Google Chrome",
            "Tor"
        ],

        datasets: [{
            label: "Processes",

            data: [
                15,
                25,
                5,
                55
            ],
        }]
    };
    
    let doughnut = document.getElementById("doughnut").getContext("2d");

    let doughnutChart = new Chart(doughnut, {
        type: "doughnut",
        data: data,
        options: options
    });

    doughnut.width = 100;
    doughnut.height = 100;
});

function initialInformation() {
    //const IP = "172.30.128.212";
    //const IP = "192.168.86.30";
    const IP = "172.20.10.5";
    const port = "5050";
    
    console.log("Starting socket...")
    const socket = new WebSocket("ws://" + IP + ":" + port);
    
    socket.onopen = () => {
        console.log("Connected!")
        socket.send("initialInformation");
        socket.close();
        console.log("Sent Message!");
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}