function fetchCurrentDataset() {
    const Dataset = [
        { patientId: 1, name: "Tommy", condition: "Lung Cancer" },
        { patientId: 2, name: "Rowlyer", condition: "Can't Sleep" },
    ];
    return Dataset;
}

function displayCurrentDataset() {
    const dataset = fetchCurrentDataset();
    const datasetDisplay = document.getElementById('dataset-display');
    datasetDisplay.innerHTML = `<pre>${JSON.stringify(dataset, null, 2)}</pre>`;
}

function exportDataset() {
    const dataset = fetchCurrentDataset();
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(dataset));
    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download", "dataset.json");
    document.body.appendChild(downloadAnchorNode);
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
}

document.addEventListener('DOMContentLoaded', (event) => {
    displayCurrentDataset();

    const exportButton = document.getElementById('export-dataset');
    exportButton.addEventListener('click', exportDataset);
});