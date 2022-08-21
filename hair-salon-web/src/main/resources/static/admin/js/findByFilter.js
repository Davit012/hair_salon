$('#selectedResourceType').on('change', function () {
    let resourceType = $('#selectedResourceType').val();
    window.location = 'resources?resourceType=' + resourceType;
});