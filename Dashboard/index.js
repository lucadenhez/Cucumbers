function initialInformation() {
    const socket = new WebSocket("ws://192.168.86.30:5050")

    console.log("Starting socket...")
    
    socket.onopen = () => {
        socket.send("initialInformation");
    }

    console.log("Done!")
}