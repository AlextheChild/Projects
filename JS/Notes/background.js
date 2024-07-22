// sets default storage values
chrome.runtime.onInstalled.addListener(
    () => {
        chrome.storage.local.set({
            open: false,
            color: "255, 255, 255",
            urls: [],
            texts: []
        });
    }
);

// set action
chrome.action.onClicked.addListener(
    async (tab) => {
        chrome.storage.local.get(
            ["open"],
            function (data) {
                if (!data.open) {
                    chrome.scripting.executeScript({
                        files: ["sidebar.js"],
                        target: { tabId: tab.id },
                    });
                } else {
                    chrome.tabs.reload(tab.id);
                }
                chrome.storage.local.set({ open: !data.open });
            }
        );
    }
);